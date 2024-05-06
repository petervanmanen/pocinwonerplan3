import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bouwdeel from './bouwdeel';
import BouwdeelDetail from './bouwdeel-detail';
import BouwdeelUpdate from './bouwdeel-update';
import BouwdeelDeleteDialog from './bouwdeel-delete-dialog';

const BouwdeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bouwdeel />} />
    <Route path="new" element={<BouwdeelUpdate />} />
    <Route path=":id">
      <Route index element={<BouwdeelDetail />} />
      <Route path="edit" element={<BouwdeelUpdate />} />
      <Route path="delete" element={<BouwdeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BouwdeelRoutes;
