import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Generalisatie from './generalisatie';
import GeneralisatieDetail from './generalisatie-detail';
import GeneralisatieUpdate from './generalisatie-update';
import GeneralisatieDeleteDialog from './generalisatie-delete-dialog';

const GeneralisatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Generalisatie />} />
    <Route path="new" element={<GeneralisatieUpdate />} />
    <Route path=":id">
      <Route index element={<GeneralisatieDetail />} />
      <Route path="edit" element={<GeneralisatieUpdate />} />
      <Route path="delete" element={<GeneralisatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GeneralisatieRoutes;
