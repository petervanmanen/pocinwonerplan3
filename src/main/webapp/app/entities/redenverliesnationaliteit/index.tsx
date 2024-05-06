import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Redenverliesnationaliteit from './redenverliesnationaliteit';
import RedenverliesnationaliteitDetail from './redenverliesnationaliteit-detail';
import RedenverliesnationaliteitUpdate from './redenverliesnationaliteit-update';
import RedenverliesnationaliteitDeleteDialog from './redenverliesnationaliteit-delete-dialog';

const RedenverliesnationaliteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Redenverliesnationaliteit />} />
    <Route path="new" element={<RedenverliesnationaliteitUpdate />} />
    <Route path=":id">
      <Route index element={<RedenverliesnationaliteitDetail />} />
      <Route path="edit" element={<RedenverliesnationaliteitUpdate />} />
      <Route path="delete" element={<RedenverliesnationaliteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RedenverliesnationaliteitRoutes;
