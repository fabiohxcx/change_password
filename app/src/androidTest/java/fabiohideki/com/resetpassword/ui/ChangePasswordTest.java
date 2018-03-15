package fabiohideki.com.resetpassword.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import fabiohideki.com.resetpassword.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.pressImeActionButton;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ChangePasswordTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void changePasswordTest() {
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.et_new_password),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_new_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("111111"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.et_new_password), withText("111111"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_new_password),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(pressImeActionButton());

        ViewInteraction textInputEditText3 = onView(
                allOf(withId(R.id.et_new_password_confirm),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_new_password_confirm),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText3.perform(replaceText("111111"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withId(R.id.et_new_password_confirm), withText("111111"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.til_new_password_confirm),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText4.perform(pressImeActionButton());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.bt_confirm), withText("Confirmar"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.sv_content),
                                        0),
                                4)));
        appCompatButton.perform(scrollTo(), click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_registration_sucessfull), withText("Cadastro realizado com sucesso ;)"),
                        childAtPosition(
                                childAtPosition(
                                        IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class),
                                        1),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Cadastro realizado com sucesso ;)")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
