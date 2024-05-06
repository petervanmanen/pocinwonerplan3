import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Openbareactiviteit from './openbareactiviteit';
import OpenbareactiviteitDetail from './openbareactiviteit-detail';
import OpenbareactiviteitUpdate from './openbareactiviteit-update';
import OpenbareactiviteitDeleteDialog from './openbareactiviteit-delete-dialog';

const OpenbareactiviteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Openbareactiviteit />} />
    <Route path="new" element={<OpenbareactiviteitUpdate />} />
    <Route path=":id">
      <Route index element={<OpenbareactiviteitDetail />} />
      <Route path="edit" element={<OpenbareactiviteitUpdate />} />
      <Route path="delete" element={<OpenbareactiviteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OpenbareactiviteitRoutes;
