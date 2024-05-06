import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ondersteunendwegdeel from './ondersteunendwegdeel';
import OndersteunendwegdeelDetail from './ondersteunendwegdeel-detail';
import OndersteunendwegdeelUpdate from './ondersteunendwegdeel-update';
import OndersteunendwegdeelDeleteDialog from './ondersteunendwegdeel-delete-dialog';

const OndersteunendwegdeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ondersteunendwegdeel />} />
    <Route path="new" element={<OndersteunendwegdeelUpdate />} />
    <Route path=":id">
      <Route index element={<OndersteunendwegdeelDetail />} />
      <Route path="edit" element={<OndersteunendwegdeelUpdate />} />
      <Route path="delete" element={<OndersteunendwegdeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OndersteunendwegdeelRoutes;
