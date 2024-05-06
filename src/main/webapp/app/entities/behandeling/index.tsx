import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Behandeling from './behandeling';
import BehandelingDetail from './behandeling-detail';
import BehandelingUpdate from './behandeling-update';
import BehandelingDeleteDialog from './behandeling-delete-dialog';

const BehandelingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Behandeling />} />
    <Route path="new" element={<BehandelingUpdate />} />
    <Route path=":id">
      <Route index element={<BehandelingDetail />} />
      <Route path="edit" element={<BehandelingUpdate />} />
      <Route path="delete" element={<BehandelingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BehandelingRoutes;
