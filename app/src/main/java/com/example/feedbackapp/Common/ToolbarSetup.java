package com.example.feedbackapp.Common;

import android.content.Context;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.feedbackapp.R;


/**
 * Created by Admin on 07/03/2017.
 */

public class ToolbarSetup
{
   private Toolbar toolbar;
   private Context context;

   public ToolbarSetup(AppCompatActivity context, Toolbar toolbar, String title, int backIcon)
   {
      this.context = context;
      this.toolbar = toolbar;

      if (toolbar != null)
      {

         if (!title.matches(""))
         {
            toolbar.setTitle(title);
         }
         if (backIcon != 0)
         {
            toolbar.setNavigationIcon(backIcon);
         }else
         {
            toolbar.setNavigationIcon(R.drawable.back_icon);
         }
         context.setSupportActionBar(toolbar);
      }

   }

   public void hideToolbar()
   {
      if (toolbar != null)
      {
         toolbar.setVisibility(View.GONE);
      }
   }
}
