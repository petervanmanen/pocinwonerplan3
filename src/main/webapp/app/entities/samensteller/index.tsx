import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Samensteller from './samensteller';
import SamenstellerDetail from './samensteller-detail';
import SamenstellerUpdate from './samensteller-update';
import SamenstellerDeleteDialog from './samensteller-delete-dialog';

const SamenstellerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Samensteller />} />
    <Route path="new" element={<SamenstellerUpdate />} />
    <Route path=":id">
      <Route index element={<SamenstellerDetail />} />
      <Route path="edit" element={<SamenstellerUpdate />} />
      <Route path="delete" element={<SamenstellerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SamenstellerRoutes;
