package javacode.unittests;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class SpecialMethodsRunner extends BlockJUnit4ClassRunner {
	private FilterMethods filter;
	
	public SpecialMethodsRunner(Class<?> theClass) throws Exception{
		super(theClass);
		filter = theClass.getAnnotation(FilterMethods.class);
	}
	
	@Override
	protected Statement childrenInvoker(final RunNotifier notifier){
		if (filter == null || filter.methods() == null || filter.methods().length <=0) {
			return super.childrenInvoker(notifier);
		}
		return new Statement() {
			
			@Override
			public void evaluate() throws Throwable {
				runMethodsWithFilter(notifier,filter);
			}
		};
	}
	
	private void runMethodsWithFilter(final RunNotifier notifier,final FilterMethods filterMethods){
		String[] filters = filterMethods.methods();
		if (filters ==null || filters.length <= 0) {
			return;
		}
		
		Set<Pattern> patterns = new HashSet<>();
		for (String filterStr : filters) {
			patterns.add(Pattern.compile(filterStr));
		}
		
		for (FrameworkMethod method : getChildren()) {
			for (Pattern pattern : patterns) {
				if (pattern.matcher(method.getName()).matches()) {
					runChild(method, notifier);
					break;
				}
			}
		}
	}
	
}//class
