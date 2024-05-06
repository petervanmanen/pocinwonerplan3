import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aansluitput from './aansluitput';
import AansluitputDetail from './aansluitput-detail';
import AansluitputUpdate from './aansluitput-update';
import AansluitputDeleteDialog from './aansluitput-delete-dialog';

const AansluitputRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aansluitput />} />
    <Route path="new" element={<AansluitputUpdate />} />
    <Route path=":id">
      <Route index element={<AansluitputDetail />} />
      <Route path="edit" element={<AansluitputUpdate />} />
      <Route path="delete" element={<AansluitputDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AansluitputRoutes;
