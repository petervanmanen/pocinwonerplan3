import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Paal from './paal';
import PaalDetail from './paal-detail';
import PaalUpdate from './paal-update';
import PaalDeleteDialog from './paal-delete-dialog';

const PaalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Paal />} />
    <Route path="new" element={<PaalUpdate />} />
    <Route path=":id">
      <Route index element={<PaalDetail />} />
      <Route path="edit" element={<PaalUpdate />} />
      <Route path="delete" element={<PaalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PaalRoutes;
