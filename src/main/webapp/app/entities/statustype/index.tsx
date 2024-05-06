import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Statustype from './statustype';
import StatustypeDetail from './statustype-detail';
import StatustypeUpdate from './statustype-update';
import StatustypeDeleteDialog from './statustype-delete-dialog';

const StatustypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Statustype />} />
    <Route path="new" element={<StatustypeUpdate />} />
    <Route path=":id">
      <Route index element={<StatustypeDetail />} />
      <Route path="edit" element={<StatustypeUpdate />} />
      <Route path="delete" element={<StatustypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StatustypeRoutes;
