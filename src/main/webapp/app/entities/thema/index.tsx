import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Thema from './thema';
import ThemaDetail from './thema-detail';
import ThemaUpdate from './thema-update';
import ThemaDeleteDialog from './thema-delete-dialog';

const ThemaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Thema />} />
    <Route path="new" element={<ThemaUpdate />} />
    <Route path=":id">
      <Route index element={<ThemaDetail />} />
      <Route path="edit" element={<ThemaUpdate />} />
      <Route path="delete" element={<ThemaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ThemaRoutes;
