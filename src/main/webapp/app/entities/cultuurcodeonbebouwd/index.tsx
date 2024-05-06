import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cultuurcodeonbebouwd from './cultuurcodeonbebouwd';
import CultuurcodeonbebouwdDetail from './cultuurcodeonbebouwd-detail';
import CultuurcodeonbebouwdUpdate from './cultuurcodeonbebouwd-update';
import CultuurcodeonbebouwdDeleteDialog from './cultuurcodeonbebouwd-delete-dialog';

const CultuurcodeonbebouwdRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cultuurcodeonbebouwd />} />
    <Route path="new" element={<CultuurcodeonbebouwdUpdate />} />
    <Route path=":id">
      <Route index element={<CultuurcodeonbebouwdDetail />} />
      <Route path="edit" element={<CultuurcodeonbebouwdUpdate />} />
      <Route path="delete" element={<CultuurcodeonbebouwdDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CultuurcodeonbebouwdRoutes;
