import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Redenverkrijgingnationaliteit from './redenverkrijgingnationaliteit';
import RedenverkrijgingnationaliteitDetail from './redenverkrijgingnationaliteit-detail';
import RedenverkrijgingnationaliteitUpdate from './redenverkrijgingnationaliteit-update';
import RedenverkrijgingnationaliteitDeleteDialog from './redenverkrijgingnationaliteit-delete-dialog';

const RedenverkrijgingnationaliteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Redenverkrijgingnationaliteit />} />
    <Route path="new" element={<RedenverkrijgingnationaliteitUpdate />} />
    <Route path=":id">
      <Route index element={<RedenverkrijgingnationaliteitDetail />} />
      <Route path="edit" element={<RedenverkrijgingnationaliteitUpdate />} />
      <Route path="delete" element={<RedenverkrijgingnationaliteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RedenverkrijgingnationaliteitRoutes;
