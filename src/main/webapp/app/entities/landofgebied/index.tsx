import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Landofgebied from './landofgebied';
import LandofgebiedDetail from './landofgebied-detail';
import LandofgebiedUpdate from './landofgebied-update';
import LandofgebiedDeleteDialog from './landofgebied-delete-dialog';

const LandofgebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Landofgebied />} />
    <Route path="new" element={<LandofgebiedUpdate />} />
    <Route path=":id">
      <Route index element={<LandofgebiedDetail />} />
      <Route path="edit" element={<LandofgebiedUpdate />} />
      <Route path="delete" element={<LandofgebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LandofgebiedRoutes;
