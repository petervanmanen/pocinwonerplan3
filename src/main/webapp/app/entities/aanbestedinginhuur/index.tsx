import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanbestedinginhuur from './aanbestedinginhuur';
import AanbestedinginhuurDetail from './aanbestedinginhuur-detail';
import AanbestedinginhuurUpdate from './aanbestedinginhuur-update';
import AanbestedinginhuurDeleteDialog from './aanbestedinginhuur-delete-dialog';

const AanbestedinginhuurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanbestedinginhuur />} />
    <Route path="new" element={<AanbestedinginhuurUpdate />} />
    <Route path=":id">
      <Route index element={<AanbestedinginhuurDetail />} />
      <Route path="edit" element={<AanbestedinginhuurUpdate />} />
      <Route path="delete" element={<AanbestedinginhuurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanbestedinginhuurRoutes;
