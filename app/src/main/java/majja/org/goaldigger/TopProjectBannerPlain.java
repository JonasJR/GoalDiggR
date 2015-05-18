package majja.org.goaldigger;

/**
 * Created by Goaldigger on 2015-04-12.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class TopProjectBannerPlain extends Fragment{

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_projekt_fragment_plain, container, false);
        Button maintext = (Button)view.findViewById(R.id.plainmainbutton);
        maintext.setText(getActivity().getTitle());
        return view;
    }
}