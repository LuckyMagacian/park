package net.imwork.yangyuanjian.common.interfaces;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * Created by thunderobot on 2017/11/18.
 */
public interface GetEnumType{
    /**
     * 枚举类专用的方法,非枚举类中调用会出现{@link IllegalStateException}<br>
     * 用于根据枚举了的value值来获取对应的枚举量<br>
     * @param value	枚举量的值<br>
     * @return 对应的枚举量
     */
    @SuppressWarnings("unchecked")
    public static <T extends Enum<?>> T getType(String value) {
        Class<?> clazz=null;
        try {
            String className=Thread.currentThread().getStackTrace()[2].getClassName();
            clazz=Class.forName(className);
            if(!Enum.class.isAssignableFrom(clazz))
                throw new IllegalStateException("method : \"getType\" can only be invoked in class which extends enum !");
            clazz.getDeclaredField("value");
            T[] all=(T[]) clazz.getMethod("values").invoke(null);
            Optional<T> opt= Stream.of(all).filter(
                    (e)->e.toString().equals(value)
            ).findAny();
            return opt.equals(Optional.empty())?null:opt.get();
        }
        catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("枚举类:"+clazz.getName()+"中不包含value字段!");
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    default boolean equals(String value){
		Class<?> clazz=null;
		try {
			clazz=this.getClass();
			if(!Enum.class.isAssignableFrom(clazz))
				throw new IllegalStateException("method : \"getType\" can only be invoked in class which extends enum !");
			Field field=clazz.getDeclaredField("value");
			field.setAccessible(true);
			Object obj=field.get(this);
			if(obj!=null)
				return obj.equals(value);
			if(value!=null)
				return value.equals(obj);
			return true;
		}
		catch (NoSuchFieldException e) {
			throw new IllegalArgumentException("枚举类:"+clazz.getName()+"中不包含value字段!");
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}