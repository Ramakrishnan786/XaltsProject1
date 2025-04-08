package StepDefinitions;

import io.cucumber.plugin.EventListener;
import io.cucumber.plugin.event.*;

import io.cucumber.plugin.ConcurrentEventListener;
import io.cucumber.plugin.event.*;

public class StepTracker implements ConcurrentEventListener {

    private static final ThreadLocal<String> currentStep = new ThreadLocal<>();

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestStepStarted.class, this::handleStepStarted);
    }

    private void handleStepStarted(TestStepStarted event) {
        if (event.getTestStep() instanceof PickleStepTestStep) {
            PickleStepTestStep pickleStep = (PickleStepTestStep) event.getTestStep();
            currentStep.set(pickleStep.getStep().getText());
        }
    }

    public static String getCurrentStep() {
        return currentStep.get();
    }
}

