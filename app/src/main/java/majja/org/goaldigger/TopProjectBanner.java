package majja.org.goaldigger;

/**
 * Created by Anton on 2015-04-12.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TopProjectBanner extends Fragment{
    private Button bigLabel;
    private Button smallButton;
    private DB db= DB.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_projekt_fragment, container, false);
        bigLabel = (Button)view.findViewById(R.id.mainFragmentButton);
        bigLabel.setText("apa");

        smallButton= (Button)view.findViewById(R.id.menuButton);
        smallButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

            }
            });
        return view;
    }
}
