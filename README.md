# Clean Architecture 分层架构

## 分层思想

![cleanArchitecture.png](doc%2Fimages%2FcleanArchitecture.png)

* 表示层：处理与UI的交互和数据ViewModel的数据展示
* 领域层：处理业务逻辑，不包含数据存储逻辑
* 数据层：处理数据存储逻辑，不包含业务逻辑

### 官方架构图

[官方建议文档](https://developer.android.google.cn/topic/architecture?hl=zh-cn)

#### 界面层(表示层)

<img src="./doc/images/mad-arch-overview-ui.png" width="60%">

界面层（或呈现层）的作用是在屏幕上显示应用数据。每当数据发生变化时，无论是因为用户互动（例如按了某个按钮），还是因为外部输入（例如网络响应），界面都应随之更新。

__界面层由以下两部分组成：__

* 在屏幕上呈现数据的界面元素。您可以使用 View 或 Jetpack Compose 函数构建这些元素。
* 用于存储数据(LiveData)、向界面提供数据以及处理逻辑的状态容器（如 ViewModel 类）。

#### 网络层(领域层)

<img src="./doc/images/mad-arch-overview-domain.png" width="60%">
网域层负责封装复杂的业务逻辑，或者由多个 ViewModel 重复使用的简单业务逻辑。__每个用例都应仅负责单个功能.__

#### 数据层

<img src="./doc/images/mad-arch-overview-data.png" width="60%">

__数据层包含业务逻辑。__ 数据层由多个仓库组成，其中每个仓库都可以包含零到多个数据源。

### Android 交互流程

![flow.png](doc%2Fimages%2Fflow.png)

## 项目目录结构

``` text
project
├───common   公共组件（多组件使用）
├───data   数据层（网络数据操作、本地数据操作）
│   ├───common
│   ├───dao
│   ├───di
│   ├───dto
│   └───entity
├───domain  领域层（处理业务逻辑）
│   ├───di
│   ├───model
│   ├───repository
│   └───usecases
└───ui  UI层（处理UI布局和交互逻辑）
    ├───dashboard
    ├───di
    ├───home
    ├───merge
    └───notifications
```

*上层服务依赖下层服务：*
公共组件提供通用能力，如果通过配置获取不同的url(由app/ui层声明注入);

- data -> domain
- domain -> ui

__data层为上层数据做支撑__

1. data层数据拆分为更细的组件，比如：`MaterialData` 模块，`AssetData` 模块，`DicData` 模块，`GameData` 模块。
2. domain层根据不同的应用场景组合依赖需要的data组件，并实现对应的逻辑。
3. ui层实现ui逻辑。

> `data` 作为数据底座，提供具体的数据访问能力，比如：网络请求、数据库访问、文件访问等等。 `domain`
> 对应具体应用包的业务逻辑； `ui` 对应具体应用包的UI逻辑。

## 依赖组件

### hilt 依赖注入组件

负责注入数据层和UI层, 对应的组件为：`/di`目录。    
`DictionaryRepository`配置支持依赖注入，上层服务`ViewModule`就可以注入数据层服务。

```java

@Module
@InstallIn(ViewModelComponent.class)
public class DictionaryRepositoryDiConfig {
    @Provides
    public DictionaryRepository provideUserMaterialRepository(DicDao dicDao) {
        return new DictionaryRepository(dicDao);
    }
}
```

ViewModule引用DictionaryRepository,使用`@HiltViewModel`和`@Inject`注解

```java

@HiltViewModel
public class MergeViewModel extends ViewModel {
    private final DictionaryRepository dictionaryRepository;

    @Inject
    public MergeViewModel(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }
}
```

### Retrofit 网络组件

负责网络请求, 定义请求接口及请求参数和响应数据对象，对应组件为：`/data/dao`目录。

```java
public interface DicDao {
    @POST("/service/app/dic/list")
    Call<ApiResponse<MergeGiftBoxConfig>> queryDicByCodes(@Body DicRequest request);
}
```

通过 Hilt 创建 Retrofit 实例，并配置网络请求参数，对应组件为：`/data/di`目录。

```java

@Module
@InstallIn(ViewModelComponent.class)
public class RetrofitDiModule {
    private String baseUrl = "https://api.baidu.site";

    @Provides
    public Retrofit provideRetrofit() {
        // 创建日志拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // 打印请求头 + 请求体
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // 创建OkHttpClient
        OkHttpClient client =
                new OkHttpClient.Builder().addInterceptor(new HeaderInterceptor()).addInterceptor(logging).build();
        // 创建Retrofit
        return new Retrofit.Builder().baseUrl(baseUrl).client(client).addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
```

创建网络请求接口实例，对应组件为：`/data/di`目录。

```java

@Module
@InstallIn(ViewModelComponent.class)
public class DicDaoDiModule {
    @Provides
    public DicDao provideDicDao(Retrofit retrofit) {
        return retrofit.create(DicDao.class);
    }
}
```

---
采用 `DataBinding` 双向绑定，使用 `LiveData` 封装数据，使用 `ViewModel` 封装业务逻辑。

1. `fragment_merge.xml` 代码改造，通过 `variable` 定义数据模型变量，并绑定到 `viewModel` 中。xml中通过 `@{viewModel.xxx}`
   变量绑定数据和方法，双向绑定要使用`@={viewModel.xxx}`针对输入框要使用。

```xml

<layout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        xmlns:tools="http://schemas.android.com/tools">

    <data>
        <variable
                name="viewModel"
                type="com.onenet.mytravelworld.ui.merge.MergeViewModel"/>
    </data>
</layout>
```

2. 修改 `MergeFragment` 通过绑定 `DataBindingUtil`绑定 `ViewModel`.

```java
public class MergeFragment extends Fragment {
    private FragmentMergeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_merge, container, false);
        binding.setViewModel(mergeViewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        return binding.getRoot();
    }
}

```


