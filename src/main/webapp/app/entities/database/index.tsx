import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Database from './database';
import DatabaseDetail from './database-detail';
import DatabaseUpdate from './database-update';
import DatabaseDeleteDialog from './database-delete-dialog';

const DatabaseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Database />} />
    <Route path="new" element={<DatabaseUpdate />} />
    <Route path=":id">
      <Route index element={<DatabaseDetail />} />
      <Route path="edit" element={<DatabaseUpdate />} />
      <Route path="delete" element={<DatabaseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DatabaseRoutes;
