import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inzet from './inzet';
import InzetDetail from './inzet-detail';
import InzetUpdate from './inzet-update';
import InzetDeleteDialog from './inzet-delete-dialog';

const InzetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inzet />} />
    <Route path="new" element={<InzetUpdate />} />
    <Route path=":id">
      <Route index element={<InzetDetail />} />
      <Route path="edit" element={<InzetUpdate />} />
      <Route path="delete" element={<InzetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InzetRoutes;
