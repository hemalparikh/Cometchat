// Generated by view binder compiler. Do not edit!
package com.cometchat.kotlinsampleapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cometchat.chatuikit.messagecomposer.CometChatMessageComposer;
import com.cometchat.kotlinsampleapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMessageComposerBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final CometChatMessageComposer composer;

  @NonNull
  public final RelativeLayout parentView;

  private FragmentMessageComposerBinding(@NonNull RelativeLayout rootView,
      @NonNull CometChatMessageComposer composer, @NonNull RelativeLayout parentView) {
    this.rootView = rootView;
    this.composer = composer;
    this.parentView = parentView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMessageComposerBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMessageComposerBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_message_composer, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMessageComposerBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.composer;
      CometChatMessageComposer composer = ViewBindings.findChildViewById(rootView, id);
      if (composer == null) {
        break missingId;
      }

      RelativeLayout parentView = (RelativeLayout) rootView;

      return new FragmentMessageComposerBinding((RelativeLayout) rootView, composer, parentView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
