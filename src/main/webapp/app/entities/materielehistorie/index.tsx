import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Materielehistorie from './materielehistorie';
import MaterielehistorieDetail from './materielehistorie-detail';
import MaterielehistorieUpdate from './materielehistorie-update';
import MaterielehistorieDeleteDialog from './materielehistorie-delete-dialog';

const MaterielehistorieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Materielehistorie />} />
    <Route path="new" element={<MaterielehistorieUpdate />} />
    <Route path=":id">
      <Route index element={<MaterielehistorieDetail />} />
      <Route path="edit" element={<MaterielehistorieUpdate />} />
      <Route path="delete" element={<MaterielehistorieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaterielehistorieRoutes;
