import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Eobjecttype from './eobjecttype';
import EobjecttypeDetail from './eobjecttype-detail';
import EobjecttypeUpdate from './eobjecttype-update';
import EobjecttypeDeleteDialog from './eobjecttype-delete-dialog';

const EobjecttypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Eobjecttype />} />
    <Route path="new" element={<EobjecttypeUpdate />} />
    <Route path=":id">
      <Route index element={<EobjecttypeDetail />} />
      <Route path="edit" element={<EobjecttypeUpdate />} />
      <Route path="delete" element={<EobjecttypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EobjecttypeRoutes;
