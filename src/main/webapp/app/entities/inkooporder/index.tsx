import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inkooporder from './inkooporder';
import InkooporderDetail from './inkooporder-detail';
import InkooporderUpdate from './inkooporder-update';
import InkooporderDeleteDialog from './inkooporder-delete-dialog';

const InkooporderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inkooporder />} />
    <Route path="new" element={<InkooporderUpdate />} />
    <Route path=":id">
      <Route index element={<InkooporderDetail />} />
      <Route path="edit" element={<InkooporderUpdate />} />
      <Route path="delete" element={<InkooporderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InkooporderRoutes;
