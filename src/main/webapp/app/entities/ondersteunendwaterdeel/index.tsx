import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ondersteunendwaterdeel from './ondersteunendwaterdeel';
import OndersteunendwaterdeelDetail from './ondersteunendwaterdeel-detail';
import OndersteunendwaterdeelUpdate from './ondersteunendwaterdeel-update';
import OndersteunendwaterdeelDeleteDialog from './ondersteunendwaterdeel-delete-dialog';

const OndersteunendwaterdeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ondersteunendwaterdeel />} />
    <Route path="new" element={<OndersteunendwaterdeelUpdate />} />
    <Route path=":id">
      <Route index element={<OndersteunendwaterdeelDetail />} />
      <Route path="edit" element={<OndersteunendwaterdeelUpdate />} />
      <Route path="delete" element={<OndersteunendwaterdeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OndersteunendwaterdeelRoutes;
