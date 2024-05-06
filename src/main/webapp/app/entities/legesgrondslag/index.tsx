import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Legesgrondslag from './legesgrondslag';
import LegesgrondslagDetail from './legesgrondslag-detail';
import LegesgrondslagUpdate from './legesgrondslag-update';
import LegesgrondslagDeleteDialog from './legesgrondslag-delete-dialog';

const LegesgrondslagRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Legesgrondslag />} />
    <Route path="new" element={<LegesgrondslagUpdate />} />
    <Route path=":id">
      <Route index element={<LegesgrondslagDetail />} />
      <Route path="edit" element={<LegesgrondslagUpdate />} />
      <Route path="delete" element={<LegesgrondslagDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LegesgrondslagRoutes;
