import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjecttypee from './eobjecttypee';
import EobjecttypeeDetail from './eobjecttypee-detail';
import EobjecttypeeUpdate from './eobjecttypee-update';
import EobjecttypeeDeleteDialog from './eobjecttypee-delete-dialog';

const EobjecttypeeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjecttypee />} />
    <Route path="new" element={<EobjecttypeeUpdate />} />
    <Route path=":id">
      <Route index element={<EobjecttypeeDetail />} />
      <Route path="edit" element={<EobjecttypeeUpdate />} />
      <Route path="delete" element={<EobjecttypeeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjecttypeeRoutes;
