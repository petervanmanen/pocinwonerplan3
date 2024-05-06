import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Opschortingzaak from './opschortingzaak';
import OpschortingzaakDetail from './opschortingzaak-detail';
import OpschortingzaakUpdate from './opschortingzaak-update';
import OpschortingzaakDeleteDialog from './opschortingzaak-delete-dialog';

const OpschortingzaakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Opschortingzaak />} />
    <Route path="new" element={<OpschortingzaakUpdate />} />
    <Route path=":id">
      <Route index element={<OpschortingzaakDetail />} />
      <Route path="edit" element={<OpschortingzaakUpdate />} />
      <Route path="delete" element={<OpschortingzaakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OpschortingzaakRoutes;
