import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Wegdeel from './wegdeel';
import WegdeelDetail from './wegdeel-detail';
import WegdeelUpdate from './wegdeel-update';
import WegdeelDeleteDialog from './wegdeel-delete-dialog';

const WegdeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Wegdeel />} />
    <Route path="new" element={<WegdeelUpdate />} />
    <Route path=":id">
      <Route index element={<WegdeelDetail />} />
      <Route path="edit" element={<WegdeelUpdate />} />
      <Route path="delete" element={<WegdeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WegdeelRoutes;
