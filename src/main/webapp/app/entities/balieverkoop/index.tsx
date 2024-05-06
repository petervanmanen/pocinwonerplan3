import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Balieverkoop from './balieverkoop';
import BalieverkoopDetail from './balieverkoop-detail';
import BalieverkoopUpdate from './balieverkoop-update';
import BalieverkoopDeleteDialog from './balieverkoop-delete-dialog';

const BalieverkoopRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Balieverkoop />} />
    <Route path="new" element={<BalieverkoopUpdate />} />
    <Route path=":id">
      <Route index element={<BalieverkoopDetail />} />
      <Route path="edit" element={<BalieverkoopUpdate />} />
      <Route path="delete" element={<BalieverkoopDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BalieverkoopRoutes;
