import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Nationaliteit from './nationaliteit';
import NationaliteitDetail from './nationaliteit-detail';
import NationaliteitUpdate from './nationaliteit-update';
import NationaliteitDeleteDialog from './nationaliteit-delete-dialog';

const NationaliteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Nationaliteit />} />
    <Route path="new" element={<NationaliteitUpdate />} />
    <Route path=":id">
      <Route index element={<NationaliteitDetail />} />
      <Route path="edit" element={<NationaliteitUpdate />} />
      <Route path="delete" element={<NationaliteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NationaliteitRoutes;
