import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Doos from './doos';
import DoosDetail from './doos-detail';
import DoosUpdate from './doos-update';
import DoosDeleteDialog from './doos-delete-dialog';

const DoosRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Doos />} />
    <Route path="new" element={<DoosUpdate />} />
    <Route path=":id">
      <Route index element={<DoosDetail />} />
      <Route path="edit" element={<DoosUpdate />} />
      <Route path="delete" element={<DoosDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DoosRoutes;
