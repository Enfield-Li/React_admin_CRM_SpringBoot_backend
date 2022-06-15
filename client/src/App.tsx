import * as React from "react";
import {
  Admin,
  Resource,
  ListGuesser,
  defaultTheme,
  fetchUtils,
} from "react-admin";
import { dataProvider } from "./dataProvider";
import { authProvider } from "./authProvider";
import Layout from "./Layout";
import contacts from "./contacts";
import companies from "./companies";
import deals from "./deals";
import { Dashboard } from "./dashboard/Dashboard";
import simpleRestProvider from "ra-data-json-server";
import { initLoginSession, myAuth, testCase } from "./myAuth";
import * as ra_core from "ra-core";

// https://stackoverflow.com/questions/72637511/react-admin-unable-to-include-credtials-in-dataprovider-with-typescript/72638247#72638247
const httpClient = (url: any, options = {} as ra_core.Options) => {
  options.credentials = "include";
  return fetchUtils.fetchJson(url, options);
};

const data = simpleRestProvider("http://localhost:3080", httpClient);
const App = () => {
  React.useEffect(() => {
    initLoginSession();
    testCase();
  }, []);

  return (
    <Admin
      // dataProvider={dataProvider}
      // authProvider={authProvider}
      dataProvider={data}
      authProvider={myAuth}
      layout={Layout}
      dashboard={Dashboard}
      theme={{
        ...defaultTheme,
        palette: {
          background: {
            default: "#fafafb",
          },
        },
      }}
    >
      <Resource name="deals" {...deals} />
      <Resource name="contacts" {...contacts} />
      <Resource name="companies" {...companies} />
      <Resource name="contactNotes" />
      <Resource name="dealNotes" />
      <Resource name="tasks" list={ListGuesser} />
      <Resource name="sales" list={ListGuesser} />
      <Resource name="tags" list={ListGuesser} />
    </Admin>
  );
};

export default App;
