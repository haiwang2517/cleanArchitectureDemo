# Clean Architecture 分层架构

## 目录结构

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
