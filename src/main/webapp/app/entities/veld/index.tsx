import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Veld from './veld';
import VeldDetail from './veld-detail';
import VeldUpdate from './veld-update';
import VeldDeleteDialog from './veld-delete-dialog';

const VeldRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Veld />} />
    <Route path="new" element={<VeldUpdate />} />
    <Route path=":id">
      <Route index element={<VeldDetail />} />
      <Route path="edit" element={<VeldUpdate />} />
      <Route path="delete" element={<VeldDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VeldRoutes;
