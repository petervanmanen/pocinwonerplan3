import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjecttypec from './eobjecttypec';
import EobjecttypecDetail from './eobjecttypec-detail';
import EobjecttypecUpdate from './eobjecttypec-update';
import EobjecttypecDeleteDialog from './eobjecttypec-delete-dialog';

const EobjecttypecRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjecttypec />} />
    <Route path="new" element={<EobjecttypecUpdate />} />
    <Route path=":id">
      <Route index element={<EobjecttypecDetail />} />
      <Route path="edit" element={<EobjecttypecUpdate />} />
      <Route path="delete" element={<EobjecttypecDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjecttypecRoutes;
