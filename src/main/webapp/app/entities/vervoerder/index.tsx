import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vervoerder from './vervoerder';
import VervoerderDetail from './vervoerder-detail';
import VervoerderUpdate from './vervoerder-update';
import VervoerderDeleteDialog from './vervoerder-delete-dialog';

const VervoerderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vervoerder />} />
    <Route path="new" element={<VervoerderUpdate />} />
    <Route path=":id">
      <Route index element={<VervoerderDetail />} />
      <Route path="edit" element={<VervoerderUpdate />} />
      <Route path="delete" element={<VervoerderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VervoerderRoutes;
