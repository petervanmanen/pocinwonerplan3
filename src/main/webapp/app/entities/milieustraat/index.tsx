import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Milieustraat from './milieustraat';
import MilieustraatDetail from './milieustraat-detail';
import MilieustraatUpdate from './milieustraat-update';
import MilieustraatDeleteDialog from './milieustraat-delete-dialog';

const MilieustraatRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Milieustraat />} />
    <Route path="new" element={<MilieustraatUpdate />} />
    <Route path=":id">
      <Route index element={<MilieustraatDetail />} />
      <Route path="edit" element={<MilieustraatUpdate />} />
      <Route path="delete" element={<MilieustraatDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MilieustraatRoutes;
