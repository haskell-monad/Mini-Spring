`Advice.java` 标识接口，表示一个`通知`，其实现可以是任何类型的`通知`，例如Interceptors

`Interceptors.java` 代表一个通用拦截器，一个通用拦截器可以拦截发生在基础程序中运行时事件,这些事件被`joinpionts`物化(实例化),
运行时`joinpoints`可以被调用,字段访问、异常...
这些接口不可以被直接使用,使用子接口来拦截特定的事件，例如：
下面的类为了实现一个Debugger的功能因此实现了一些特定的拦截器

`Joinpoint.java` 这个接口提供了一个通用的运行时joinpoint(在Aop的术语中)
一个运行时的joinpoint是一个发生在静态joinpoint中(例如:一个程序中的位置)的事件，例如：
一次调用是方法上的运行时joinpoint(静态joinpoint)
一个给定的joinpoint的静态部分可以使用getStaticPart()进行检索。
在一个拦截器框架的上下文环境中，一个运行时joinpoint是通过访问一个可访问的对象(一个方法、构造器、字段)那样物化的(实例化)，比如：
joinpoint的静态部分，它被传递到被安装到静态joinpoint上的拦截器链中
Object proceed() 此方法的实现和语义取决于实际的joinpoint类型(查看子接口)，返回子接口的定义

`Invocation.java` 这个接口表示程序中的一个调用
一个调用是一个可以被拦截器拦截的joinpoint


通知`advice` + 切入点`pointcut`(在哪些类的哪些方法上切)
连接点`joinpoint` 表示一个方法的执行





切面（Aspect）： 一个关注点的模块化，这个关注点可能会横切多个对象。事务管理是J2EE应用中一个关于横切关注点的很好的例子。 在Spring AOP中，切面可以使用通用类（基于模式的风格） 或者在普通类中以 @Aspect 注解（@AspectJ风格）来实现。
连接点（Joinpoint）： 在程序执行过程中某个特定的点，比如某方法调用的时候或者处理异常的时候。 在Spring AOP中，一个连接点 总是 代表一个方法的执行。 通过声明一个 org.aspectj.lang.JoinPoint 类型的参数可以使通知（Advice）的主体部分获得连接点信息。
通知（Advice）： 在切面的某个特定的连接点（Joinpoint）上执行的动作。通知有各种类型，其中包括“around”、“before”和“after”等通知。 通知的类型将在后面部分进行讨论。许多AOP框架，包括Spring，都是以拦截器做通知模型，并维护一个以连接点为中心的拦截器链。
切入点（Pointcut）： 匹配连接点（Joinpoint）的断言。通知和一个切入点表达式关联，并在满足这个切入点的连接点上运行（例如，当执行某个特定名称的方法时）。 切入点表达式如何和连接点匹配是AOP的核心：Spring缺省使用AspectJ切入点语法。
引入（Introduction）： （也被称为内部类型声明（inter-type declaration））。声明额外的方法或者某个类型的字段。 Spring允许引入新的接口（以及一个对应的实现）到任何被代理的对象。 例如，你可以使用一个引入来使bean实现 IsModified 接口，以便简化缓存机制。
目标对象（Target Object）： 被一个或者多个切面（aspect）所通知（advise）的对象。也有人把它叫做 被通知（advised） 对象。 既然Spring AOP是通过运行时代理实现的，这个对象永远是一个 被代理（proxied） 对象。
AOP代理（AOP Proxy）： AOP框架创建的对象，用来实现切面契约（aspect contract）（包括通知方法执行等功能）。 在Spring中，AOP代理可以是JDK动态代理或者CGLIB代理。 注意：Spring 2.0最新引入的基于模式（schema-based）风格和@AspectJ注解风格的切面声明，对于使用这些风格的用户来说，代理的创建是透明的。
织入（Weaving）： 把切面（aspect）连接到其它的应用程序类型或者对象上，并创建一个被通知（advised）的对象。 这些可以在编译时（例如使用AspectJ编译器），类加载时和运行时完成。 Spring和其他纯Java AOP框架一样，在运行时完成织入。

通知的类型：
前置通知（Before advice）： 在某连接点（join point）之前执行的通知，但这个通知不能阻止连接点前的执行（除非它抛出一个异常）。
返回后通知（After returning advice）： 在某连接点（join point）正常完成后执行的通知：例如，一个方法没有抛出任何异常，正常返回。
抛出异常后通知（After throwing advice）： 在方法抛出异常退出时执行的通知。
后通知（After (finally) advice）： 当某连接点退出的时候执行的通知（不论是正常返回还是异常退出）。
环绕通知（Around Advice）： 包围一个连接点（join point）的通知，如方法调用。这是最强大的一种通知类型。 环绕通知可以在方法调用前后完成自定义的行为。它也会选择是否继续执行连接点或直接返回它们自己的返回值或抛出异常来结束执行。
