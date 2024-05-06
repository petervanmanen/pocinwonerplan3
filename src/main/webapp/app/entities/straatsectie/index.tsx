import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Straatsectie from './straatsectie';
import StraatsectieDetail from './straatsectie-detail';
import StraatsectieUpdate from './straatsectie-update';
import StraatsectieDeleteDialog from './straatsectie-delete-dialog';

const StraatsectieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Straatsectie />} />
    <Route path="new" element={<StraatsectieUpdate />} />
    <Route path=":id">
      <Route index element={<StraatsectieDetail />} />
      <Route path="edit" element={<StraatsectieUpdate />} />
      <Route path="delete" element={<StraatsectieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StraatsectieRoutes;
