import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Stuwgebied from './stuwgebied';
import StuwgebiedDetail from './stuwgebied-detail';
import StuwgebiedUpdate from './stuwgebied-update';
import StuwgebiedDeleteDialog from './stuwgebied-delete-dialog';

const StuwgebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Stuwgebied />} />
    <Route path="new" element={<StuwgebiedUpdate />} />
    <Route path=":id">
      <Route index element={<StuwgebiedDetail />} />
      <Route path="edit" element={<StuwgebiedUpdate />} />
      <Route path="delete" element={<StuwgebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StuwgebiedRoutes;
