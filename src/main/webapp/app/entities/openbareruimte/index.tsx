import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Openbareruimte from './openbareruimte';
import OpenbareruimteDetail from './openbareruimte-detail';
import OpenbareruimteUpdate from './openbareruimte-update';
import OpenbareruimteDeleteDialog from './openbareruimte-delete-dialog';

const OpenbareruimteRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Openbareruimte />} />
    <Route path="new" element={<OpenbareruimteUpdate />} />
    <Route path=":id">
      <Route index element={<OpenbareruimteDetail />} />
      <Route path="edit" element={<OpenbareruimteUpdate />} />
      <Route path="delete" element={<OpenbareruimteDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OpenbareruimteRoutes;
