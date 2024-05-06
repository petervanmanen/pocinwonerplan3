import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Tegenprestatie from './tegenprestatie';
import TegenprestatieDetail from './tegenprestatie-detail';
import TegenprestatieUpdate from './tegenprestatie-update';
import TegenprestatieDeleteDialog from './tegenprestatie-delete-dialog';

const TegenprestatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Tegenprestatie />} />
    <Route path="new" element={<TegenprestatieUpdate />} />
    <Route path=":id">
      <Route index element={<TegenprestatieDetail />} />
      <Route path="edit" element={<TegenprestatieUpdate />} />
      <Route path="delete" element={<TegenprestatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TegenprestatieRoutes;
