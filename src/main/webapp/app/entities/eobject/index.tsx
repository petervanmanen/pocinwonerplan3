import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobject from './eobject';
import EobjectDetail from './eobject-detail';
import EobjectUpdate from './eobject-update';
import EobjectDeleteDialog from './eobject-delete-dialog';

const EobjectRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobject />} />
    <Route path="new" element={<EobjectUpdate />} />
    <Route path=":id">
      <Route index element={<EobjectDetail />} />
      <Route path="edit" element={<EobjectUpdate />} />
      <Route path="delete" element={<EobjectDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjectRoutes;
