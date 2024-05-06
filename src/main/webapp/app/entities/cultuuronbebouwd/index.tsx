import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cultuuronbebouwd from './cultuuronbebouwd';
import CultuuronbebouwdDetail from './cultuuronbebouwd-detail';
import CultuuronbebouwdUpdate from './cultuuronbebouwd-update';
import CultuuronbebouwdDeleteDialog from './cultuuronbebouwd-delete-dialog';

const CultuuronbebouwdRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cultuuronbebouwd />} />
    <Route path="new" element={<CultuuronbebouwdUpdate />} />
    <Route path=":id">
      <Route index element={<CultuuronbebouwdDetail />} />
      <Route path="edit" element={<CultuuronbebouwdUpdate />} />
      <Route path="delete" element={<CultuuronbebouwdDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CultuuronbebouwdRoutes;
