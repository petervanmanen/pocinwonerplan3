import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Infiltratieput from './infiltratieput';
import InfiltratieputDetail from './infiltratieput-detail';
import InfiltratieputUpdate from './infiltratieput-update';
import InfiltratieputDeleteDialog from './infiltratieput-delete-dialog';

const InfiltratieputRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Infiltratieput />} />
    <Route path="new" element={<InfiltratieputUpdate />} />
    <Route path=":id">
      <Route index element={<InfiltratieputDetail />} />
      <Route path="edit" element={<InfiltratieputUpdate />} />
      <Route path="delete" element={<InfiltratieputDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InfiltratieputRoutes;
