import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Agendapunt from './agendapunt';
import AgendapuntDetail from './agendapunt-detail';
import AgendapuntUpdate from './agendapunt-update';
import AgendapuntDeleteDialog from './agendapunt-delete-dialog';

const AgendapuntRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Agendapunt />} />
    <Route path="new" element={<AgendapuntUpdate />} />
    <Route path=":id">
      <Route index element={<AgendapuntDetail />} />
      <Route path="edit" element={<AgendapuntUpdate />} />
      <Route path="delete" element={<AgendapuntDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AgendapuntRoutes;
