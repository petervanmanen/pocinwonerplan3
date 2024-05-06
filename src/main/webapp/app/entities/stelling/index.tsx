import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Stelling from './stelling';
import StellingDetail from './stelling-detail';
import StellingUpdate from './stelling-update';
import StellingDeleteDialog from './stelling-delete-dialog';

const StellingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Stelling />} />
    <Route path="new" element={<StellingUpdate />} />
    <Route path=":id">
      <Route index element={<StellingDetail />} />
      <Route path="edit" element={<StellingUpdate />} />
      <Route path="delete" element={<StellingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StellingRoutes;
