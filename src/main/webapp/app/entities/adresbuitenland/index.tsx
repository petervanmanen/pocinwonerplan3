import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Adresbuitenland from './adresbuitenland';
import AdresbuitenlandDetail from './adresbuitenland-detail';
import AdresbuitenlandUpdate from './adresbuitenland-update';
import AdresbuitenlandDeleteDialog from './adresbuitenland-delete-dialog';

const AdresbuitenlandRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Adresbuitenland />} />
    <Route path="new" element={<AdresbuitenlandUpdate />} />
    <Route path=":id">
      <Route index element={<AdresbuitenlandDetail />} />
      <Route path="edit" element={<AdresbuitenlandUpdate />} />
      <Route path="delete" element={<AdresbuitenlandDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AdresbuitenlandRoutes;
