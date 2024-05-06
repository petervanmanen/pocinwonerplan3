import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ambacht from './ambacht';
import AmbachtDetail from './ambacht-detail';
import AmbachtUpdate from './ambacht-update';
import AmbachtDeleteDialog from './ambacht-delete-dialog';

const AmbachtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ambacht />} />
    <Route path="new" element={<AmbachtUpdate />} />
    <Route path=":id">
      <Route index element={<AmbachtDetail />} />
      <Route path="edit" element={<AmbachtUpdate />} />
      <Route path="delete" element={<AmbachtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AmbachtRoutes;
