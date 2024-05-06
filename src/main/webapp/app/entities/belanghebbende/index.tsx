import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Belanghebbende from './belanghebbende';
import BelanghebbendeDetail from './belanghebbende-detail';
import BelanghebbendeUpdate from './belanghebbende-update';
import BelanghebbendeDeleteDialog from './belanghebbende-delete-dialog';

const BelanghebbendeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Belanghebbende />} />
    <Route path="new" element={<BelanghebbendeUpdate />} />
    <Route path=":id">
      <Route index element={<BelanghebbendeDetail />} />
      <Route path="edit" element={<BelanghebbendeUpdate />} />
      <Route path="delete" element={<BelanghebbendeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BelanghebbendeRoutes;
